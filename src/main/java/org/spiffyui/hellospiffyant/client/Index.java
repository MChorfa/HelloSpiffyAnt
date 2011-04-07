/*
 * Copyright (c) 2010 Unpublished Work of Novell, Inc. All Rights Reserved.
 *
 * THIS WORK IS AN UNPUBLISHED WORK AND CONTAINS CONFIDENTIAL,
 * PROPRIETARY AND TRADE SECRET INFORMATION OF NOVELL, INC. ACCESS TO
 * THIS WORK IS RESTRICTED TO (I) NOVELL, INC. EMPLOYEES WHO HAVE A NEED
 * TO KNOW HOW TO PERFORM TASKS WITHIN THE SCOPE OF THEIR ASSIGNMENTS AND
 * (II) ENTITIES OTHER THAN NOVELL, INC. WHO HAVE ENTERED INTO
 * APPROPRIATE LICENSE AGREEMENTS. NO PART OF THIS WORK MAY BE USED,
 * PRACTICED, PERFORMED, COPIED, DISTRIBUTED, REVISED, MODIFIED,
 * TRANSLATED, ABRIDGED, CONDENSED, EXPANDED, COLLECTED, COMPILED,
 * LINKED, RECAST, TRANSFORMED OR ADAPTED WITHOUT THE PRIOR WRITTEN
 * CONSENT OF NOVELL, INC. ANY USE OR EXPLOITATION OF THIS WORK WITHOUT
 * AUTHORIZATION COULD SUBJECT THE PERPETRATOR TO CRIMINAL AND CIVIL
 * LIABILITY.
 *
 * ========================================================================
 */
package org.spiffyui.hellospiffyant.client;

import org.spiffyui.client.JSONUtil;
import org.spiffyui.client.MainFooter;
import org.spiffyui.client.MainHeader;
import org.spiffyui.client.MessageUtil;
import org.spiffyui.client.rest.RESTCallback;
import org.spiffyui.client.rest.RESTException;
import org.spiffyui.client.rest.RESTility;
import org.spiffyui.client.widgets.LongMessage;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;


/**
 * This class is the main entry point for our GWT module.
 */
public class Index implements EntryPoint, ClickHandler, KeyPressHandler 
{

    private static Index g_index;
    private TextBox m_text = new TextBox();
    private LongMessage m_longMessage = new LongMessage("longMsg");

    /**
     * The Index page constructor
     */
    public Index()
    {
        g_index = this;
    }


    @Override
    public void onModuleLoad()
    {
        MainHeader header = new MainHeader();
        header.setHeaderTitle("Hello Spiffy Ant!");
        
        MainFooter footer = new MainFooter();
        footer.setFooterString("This application is a <a href=\"http://garcia.qalab.cam.novell.com:8180/spsample/\">Spiffy UI Framework</a> application");
        
        FlowPanel panel = new FlowPanel()
        {
            @Override
            public void onLoad()
            {
                super.onLoad();
                /*
                 Let's set focus into the text field when the page first loads
                 */
                m_text.setFocus(true);
            }
        };
        
        panel.add(m_longMessage);
        final InlineLabel label = new InlineLabel("What's your name? ");
        label.setHeight("1em");
        panel.add(label);
        panel.add(m_text);
        final Button button = new Button("Submit");
        panel.add(button);
        
        button.addClickHandler(this);
        m_text.addKeyPressHandler(this);
        
        RootPanel.get("mainContent").add(panel);
    }
    
    @Override
    public void onClick(ClickEvent event)
    {
        sendRequest();
    }

    @Override
    public void onKeyPress(KeyPressEvent event)
    {
        /*
         We want to submit the request if the user pressed enter
         */
        if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
            sendRequest();
        }
    }
    
    /**
     * Send the REST request to the server and read the response back.
     */
    private void sendRequest()
    {
        String q = m_text.getValue().trim();
        if (q.equals("")) {
            MessageUtil.showWarning("Please enter your name in the text field.", false);
            return;
        }
        RESTility.callREST("simple/" + q, new RESTCallback() {
            
            @Override
            public void onSuccess(JSONValue val)
            {
                showSuccessMessage(val);
            }
            
            @Override
            public void onError(int statusCode, String errorResponse)
            {
                MessageUtil.showError("Error.  Status Code: " + statusCode + " " + errorResponse);
            }
            
            @Override
            public void onError(RESTException e)
            {
                MessageUtil.showError(e.getReason());
            }
        });
        
    }
    
    /**
     * Show the successful message result of our REST call.
     * 
     * @param val    the value containing the JSON response from the server
     */
    private void showSuccessMessage(JSONValue val)
    {
        JSONObject obj = val.isObject();
        String name = JSONUtil.getStringValue(obj, "user");
        String browser = JSONUtil.getStringValue(obj, "userAgent");
        String server = JSONUtil.getStringValue(obj, "serverInfo");
        
        String message = "Hello, " + name + "!  <br/>You are using " + browser + ".<br/>The server is " + server + ".";
        m_longMessage.setHTML(message);
    }
}
