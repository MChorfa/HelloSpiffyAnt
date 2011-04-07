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
package org.spiffyui.hellospiffyant.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet returns the user and browser and server info.
 */
public class SimpleServlet extends HttpServlet
{

    private static final long serialVersionUID = -1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException,
            IOException
    {
        String user = request.getPathInfo();
        if (user.startsWith("/")) {
            user = user.substring(1);
        }
        String serverInfo = getServletContext().getServerInfo();
        String userAgent = request.getHeader("User-Agent");
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        /*
         We just need to return some simple JSON for this REST call.
         */
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append("\"user\": \"" + user + "\"");
        sb.append(",");
        sb.append("\"userAgent\": \"" + userAgent + "\"");
        sb.append(",");
        sb.append("\"serverInfo\": \"" + serverInfo + "\"");
        sb.append("}");
        out.println(sb.toString());
    }
}
