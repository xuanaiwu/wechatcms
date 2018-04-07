package com.dayuan.utils;

import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
*
* <p class="detail">
* 功能：封装的请求处理特殊字符
* </p>
* @ClassName: TsRequest
* @version V1.0
*/
public class TsRequest extends HttpServletRequestWrapper {
          private Map params;

          public TsRequest(HttpServletRequest request, Map newParams) {
                   super(request);
                   this.params = newParams;
         }

          public Map getParameterMap() {
                   return params ;
         }

          public Enumeration getParameterNames() {
                   Vector l = new Vector( params.keySet());
                   return l.elements();
         }

          public String[] getParameterValues(String name) {
                  Object v = params.get(name);
                   if (v == null ) {
                             return null ;
                  } else if (v instanceof String[]) {
                            String[] value = (String[]) v;
                             for (int i = 0; i < value.length; i++) {
                                     value[i] = value[i].replaceAll( "<", "&lt;" );
                                     value[i] = value[i].replaceAll( ">", "&gt;" );
                                     value[i] = value[i].replaceAll("\"", "&quot;");
                                     value[i] = value[i].replaceAll("\'", "&apos;");
                                     value[i] = value[i].replaceAll("&", "&amp;");
                                     value[i] = value[i].replaceAll("%", "&pc;");
                                     value[i] = value[i].replaceAll("_", "&ul;");
                                     value[i] = value[i].replaceAll("#", "&shap;");
                                     value[i] = value[i].replaceAll("\\?", "&ques;");
                                     value[i] = value[i].replaceAll("\\*", "");
                            }
                             return (String[]) value;
                  } else if (v instanceof String) {
                            String value = (String) v;
                            value = value.replaceAll( "<", "&lt;" );
                            value = value.replaceAll( ">", "&gt;" );
                            value = value.replaceAll("\"", "&quot;");
                            value = value.replaceAll("\'", "&apos;");
                            value = value.replaceAll("&", "&amp;");
                            value = value.replaceAll("%", "&pc;");
                            value = value.replaceAll("_", "&ul;");
                            value = value.replaceAll("#", "&shap;");
                            value = value.replaceAll("\\?", "&ques;");
                            value = value.replaceAll("\\*", "");
                             return new String[] { (String) value };
                  } else {
                             return new String[] { v.toString() };
                  }
         }

          public String getParameter(String name) {
                  Object v = params.get(name);
                   if (v == null ) {
                             return null ;
                  } else if (v instanceof String[]) {
                            String[] strArr = (String[]) v;
                             if (strArr.length > 0) {
                                     String value = strArr[0];
                                     value = value.replaceAll( "<", "&lt;" );
                                     value = value.replaceAll( "<", "&gt;" );
                                     value = value.replaceAll("\"", "&quot;");
                                     value = value.replaceAll("\'", "&apos;");
                                     value = value.replaceAll("&", "&amp;");
                                     value = value.replaceAll("%", "&pc;");
                                     value = value.replaceAll("_", "&ul;");
                                     value = value.replaceAll("#", "&shap;");
                                     value = value.replaceAll("\\?", "&ques;");
                                     value = value.replaceAll("\\*", "");
                                      return value;
                            } else {
                                      return null ;
                            }
                  } else if (v instanceof String) {
                            String value = (String) v;
                            value = value.replaceAll( "<", "&lt;" );
                            value = value.replaceAll( ">", "&gt;" );
                            value = value.replaceAll("\"", "&quot;");
                            value = value.replaceAll("\'", "&apos;");
                            value = value.replaceAll("&", "&amp;");
                            value = value.replaceAll("%", "&pc;");
                            value = value.replaceAll("_", "&ul;");
                            value = value.replaceAll("#", "&shap;");
                            value = value.replaceAll("\\?", "&ques;");
                            value = value.replaceAll("\\*", "");
                             return (String) value;
                  } else {
                             return v.toString();
                  }
         }
}

