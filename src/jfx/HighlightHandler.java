package jfx;//package org.fsbwit.prog.aufgaben.regex.tester;

import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

/**
 * Ein KEy-Event-Handler, der Highlighting über RegEx macht
 */
public class HighlightHandler implements EventHandler<KeyEvent> {
    /**
     * Hier liegt der Eingabetext
     */
    private final TextArea input;

    /**
     * Hier liegt der reguläre Ausdruck
     */
    private final TextField regex;

    /**
     * Hier wird die Ausgabe hingeschrieben (als HTML)
     */
    private final WebView output;

    /**
     * ...weil leeren schneller geht als neu anlegen
     */
    private final StringBuilder sb;

    /**
     * Der Konstruktor
     * @param input Eingabe-TextArea
     * @param regex RegEx-TextField
     * @param output Ausgabe-WebView
     */
    public HighlightHandler(TextArea input, TextField regex, WebView output) {
        this.input = input;
        this.regex = regex;
        this.output = output;
        sb = new StringBuilder();
    }


    @Override
    public void handle(KeyEvent event) {
        //Zustand zurücksetzen
        sb.setLength(0);
        sb.append("<body style='font-family:monospace;font-size:20;color:black,float:left'>");
        output.getEngine().loadContent("");

        //Pattern erzeugen, bei Exception Fehlermeldung in Ausgabefeld schreiben
        Pattern p;
        try {
            p = Pattern.compile(regex.getText());
        } catch (PatternSyntaxException ex) {
            output.getEngine().loadContent(String.format("<p style='font-family:monospace;font-size:20;color:red'>RegEx ungültig: %s</p>", ex.getLocalizedMessage()));
            return;
        }


        Matcher m = p.matcher(input.getText());

        //da Treffer- und Nichttreffer-Teile in der Ausgabe erscheinen sollen, rutscht diese Adresse einmal
        // durch die ganze Eingabe.
        int lastStart = 0;

        //Wir brauchen ausnahmsweise das MatchResult, weil wir die Adressen im String benötigt werden.
        while( m.find() ){
            MatchResult result = m.toMatchResult();
            sb.append(input.getText(), lastStart, result.start());
            sb.append(String.format("<span style=color:blue;text-decoration:underline>%s</span>", result.group()));
            lastStart = result.end();
        }

        //Rest hinter dem letzten Treffer
        if( lastStart <  input.getText().length() ){
            sb.append(input.getText(), lastStart, input.getText().length());
        }

        //abschließen und ausgeben
        sb.append("</body>");
        output.getEngine().loadContent(sb.toString());

    }
}
