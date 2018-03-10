/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberpunk.bully;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ibm.watson.developer_cloud.language_translator.v2.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v2.model.TranslateOptions;
import com.ibm.watson.developer_cloud.language_translator.v2.model.TranslationResult;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.EmotionOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.EmotionScores;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.EntitiesOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.KeywordsOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.KeywordsResult;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.RelationsOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.TargetedEmotionResults;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author mnmat
 */
public class WatsonController {
     public JsonObject analyzeText(String text){
        String translatedText = translateText(text);      
        
        NaturalLanguageUnderstanding service = new NaturalLanguageUnderstanding(
            NaturalLanguageUnderstanding.VERSION_DATE_2017_02_27,
            "46b01a35-b6c8-4eda-9f1d-8f0ef2668095",
            "HWRBJwZoWLvu"
          );
        
        
        RelationsOptions relations = new RelationsOptions.Builder()
        .build();
        
        List<String> targets = new ArrayList<String>();
        String[] focusWords = {"teacher", "teachers", "colleague", "colleagues", "friend", "friends", "class", "school", "sports"};
        targets.addAll(Arrays.asList(focusWords));
        
        EmotionOptions emotion= new EmotionOptions.Builder()
          .targets(targets)
          .build();

        
        EntitiesOptions entitiesOptions = new EntitiesOptions.Builder()
        .emotion(true)
        .sentiment(true)
        .limit(2)
        .build();

        KeywordsOptions keywordsOptions = new KeywordsOptions.Builder()
        .emotion(true)
        .sentiment(true)
        .limit(2)
        .build();

        Features features = new Features.Builder()
        .entities(entitiesOptions)
        .keywords(keywordsOptions)
        .emotion(emotion)
        //.relations(relations)
        .build();

        AnalyzeOptions parameters = new AnalyzeOptions.Builder()
        .text(translatedText)
        .features(features)
        .build();

        AnalysisResults response = service
        .analyze(parameters)
        .execute();
        
        return formataJson(response, Arrays.asList(focusWords));
    }
    
    private String translateText(String text){
        LanguageTranslator service_translate = new LanguageTranslator();
        service_translate.setUsernameAndPassword("e41ac23a-1362-4544-bc48-2f846ca20811","CyGyrvhKFCz0");
                
        TranslateOptions translateOptions = new TranslateOptions.Builder()
        .addText(text)
        .modelId("pt-en")
        .build();

        TranslationResult result = service_translate.translate(translateOptions)
        .execute();

        return result.getTranslations().get(0).getTranslation();
        
    }
    
    private JsonObject formataJson(AnalysisResults response, List<String> focusWords){
        JsonObject resultado = new JsonObject();
      JsonArray palavras = new JsonArray();
            
      List<TargetedEmotionResults> targetsReturned = response.getEmotion().getTargets();
        for (TargetedEmotionResults targetedEmotionResults : targetsReturned) {
            JsonObject palavra = new JsonObject();
            palavra.addProperty("text", String.valueOf(targetedEmotionResults.getText()));    
            palavra.addProperty("anger", String.valueOf(targetedEmotionResults.getEmotion().getAnger()));    
            palavra.addProperty("disgust", String.valueOf(targetedEmotionResults.getEmotion().getDisgust())); 
            palavra.addProperty("fear", String.valueOf(targetedEmotionResults.getEmotion().getFear())); 
            palavra.addProperty("joy", String.valueOf(targetedEmotionResults.getEmotion().getJoy())); 
            palavra.addProperty("sadness", String.valueOf(targetedEmotionResults.getEmotion().getSadness())); 
            palavras.add(palavra);      
        }
        
         List<KeywordsResult> keywordsReturned = response.getKeywords();
        for (KeywordsResult wordResults : keywordsReturned) {
            String text = String.valueOf(wordResults.getText());
            if(!focusWords.contains(text)){                
                JsonObject palavra = new JsonObject();
                palavra.addProperty("text", text);    
                palavra.addProperty("anger", String.valueOf(wordResults.getEmotion().getAnger()));    
                palavra.addProperty("disgust", String.valueOf(wordResults.getEmotion().getDisgust())); 
                palavra.addProperty("fear", String.valueOf(wordResults.getEmotion().getFear())); 
                palavra.addProperty("joy", String.valueOf(wordResults.getEmotion().getJoy())); 
                palavra.addProperty("sadness", String.valueOf(wordResults.getEmotion().getSadness())); 
                palavras.add(palavra);      
            }
        }
        
        JsonObject geral = new JsonObject();
        EmotionScores docEmotion = response.getEmotion().getDocument().getEmotion();
        geral.addProperty("anger", String.valueOf(docEmotion.getAnger()));    
        geral.addProperty("disgust", String.valueOf(docEmotion.getDisgust())); 
        geral.addProperty("fear", String.valueOf(docEmotion.getFear())); 
        geral.addProperty("joy", String.valueOf(docEmotion.getJoy())); 
        geral.addProperty("sadness", String.valueOf(docEmotion.getSadness())); 
        
        resultado.add("overallEmotion", geral);
        resultado.add("words", palavras);
        
        return resultado;
    }
}
