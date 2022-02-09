package com.example.aadharscanner.utils;

import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

public class Recognizer {

    public static TextRecognizer getTextRecognizer() {

        TextRecognizer textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        return textRecognizer;
    }
}
