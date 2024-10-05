/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.utils;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.utils.StringUtils;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 *
 * @author akil
 */
public class LogUtils {

    public static String getArgsString() {
        return " IP:{} User:{}";
    }

    public static String buildStringForLog(String sqlString, Object[] params) {
        StringBuilder sb = new StringBuilder(StringUtils.escapeQuotes(sqlString)).append(" -- parameters(");
        boolean firstRun = true;
        for (Object tmpParam : params) {
            if (firstRun) {
                firstRun = false;
                sb.append(StringUtils.escapeQuotes(tmpParam.toString()));
            } else {
                sb.append(", ").append(StringUtils.escapeQuotes(tmpParam.toString()));
            }
        }
        return sb.toString();
    }

    public static String buildStringForLog(String sqlString, List<Object[]> paramList) {
        StringBuilder sb = new StringBuilder(StringUtils.escapeQuotes(sqlString)).append(" -- parameters(");
        boolean firstRun = true;
        for (Object params[] : paramList) {
            sb.append(" (");
            for (Object tmpParam : params) {
                if (firstRun) {
                    firstRun = false;
                    sb.append(StringUtils.escapeQuotes(tmpParam.toString()));
                } else {
                    sb.append(", ").append(StringUtils.escapeQuotes(tmpParam.toString()));
                }
            }
            sb.append(")");
        }
        return sb.toString();
    }

    public static String buildStringForLog(String sqlString, Map<String, Object> params) {
        StringBuilder sb = new StringBuilder(StringUtils.escapeQuotes(sqlString)).append(" -- parameters(");
        boolean firstRun = true;
        for (Map.Entry tmpEntry : params.entrySet()) {
            if (firstRun) {
                firstRun = false;
                sb.append(tmpEntry.getKey()).append(":").append(tmpEntry.getValue());
            } else {
                sb.append(", ").append(tmpEntry.getKey()).append(":").append(tmpEntry.getValue());
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public static String getIpAddress() {
        try {
            return ((WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getRemoteAddress();
        } catch (NullPointerException n) {
            return "0.0.0.0";
        }
    }

    public static String getUsername() {
        try {
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass() == CustomUserDetails.class) {
                return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            } else {
                return "";
            }
        } catch (NullPointerException n) {
            return "blank";
        }
    }
}
