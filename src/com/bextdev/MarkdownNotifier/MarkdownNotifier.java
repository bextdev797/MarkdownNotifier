package com.bextdev.MarkdownNotifier;

import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Html;
import android.text.SpannableString;
import com.youbenzi.mdtool.tool.MDTool;

public class MarkdownNotifier extends AndroidNonvisibleComponent {
  private Activity activity;

  public MarkdownNotifier(ComponentContainer container) {
    super(container.$form());
    this.activity = container.$context();
  }

  @SimpleFunction
  public void CreateMessageNotifier(String title, String message, String buttonText){
    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
    builder.setTitle(markdownToHtml(title))
           .setMessage(markdownToHtml(message))
           .setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which){
               MessageNotifierClosed();
             }
           })
           .show();
  }

  @SimpleFunction
  public void CreateChooseNotifier(String title, String message, String button1Text, String button2Text, boolean cancelable){
    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
    builder.setTitle(markdownToHtml(title))
           .setMessage(markdownToHtml(message))
           .setPositiveButton(button1Text, new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) { AfterChoosing(button1Text); }
           })
           .setNeutralButton(button2Text, new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) { AfterChoosing(button2Text); }
           });

    if(cancelable){
      builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){ 
        @Override
        public void onClick(DialogInterface dialog, int which) { ChooseNotifierCanceled(); }
      });
    }

    builder.show();
  } 

  @SimpleEvent
  public void AfterChoosing(String choice){
    EventDispatcher.dispatchEvent(this, "AfterChoosing", choice);
  }
  
  @SimpleEvent
  public void MessageNotifierClosed(){
    EventDispatcher.dispatchEvent(this, "MessageNotifierClosed");
  }

  @SimpleEvent
  public void ChooseNotifierCanceled(){
    EventDispatcher.dispatchEvent(this, "ChooseNotifierCanceled");
  }     

  private static SpannableString markdownToHtml(String string){
    return new SpannableString(Html.fromHtml(MDTool.markdown2Html(string)));
  }
}