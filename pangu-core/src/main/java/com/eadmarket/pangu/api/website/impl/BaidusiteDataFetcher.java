package com.eadmarket.pangu.api.website.impl;

import com.google.common.collect.Lists;

import com.eadmarket.pangu.api.website.WebSiteDataDO;

import java.util.List;

/**
 * 获取百度快照时间
 *
 * @author liuyongpo@gmail.com
 */
class BaidusiteDataFetcher extends AbstractDataFetcher {

  @Override
  protected List<WebSiteDataDO> exactValueFromHtml(String htmlContent) {

    List<WebSiteDataDO> list = Lists.newArrayList();

    WebSiteDataDO websiteDataDO = resolveBaiduSite(htmlContent);
    if (websiteDataDO != null) {
      list.add(websiteDataDO);
    }

    websiteDataDO = resolveBaiduPhoto(htmlContent);
    if (websiteDataDO != null) {
      list.add(websiteDataDO);
    }

    return list;
  }

  private WebSiteDataDO resolveBaiduSite(String htmlContent) {
    String baiduSite = "";
    if (htmlContent.contains("找到相关结果数") && htmlContent.contains("个")) {
      String temp = htmlContent;
      int startIndex = temp.indexOf("找到相关结果数");
      temp = temp.substring(startIndex);
      int endIndex = temp.indexOf("个");
      baiduSite = temp.substring(0, endIndex).replaceAll("[^0-9]", "").trim();
    }
    baiduSite = assignDefaultValueIfBlank(baiduSite);
    return new WebSiteDataDO("baidu", "site", baiduSite);
  }

  private WebSiteDataDO resolveBaiduPhoto(String htmlContent) {
    String photo = "";
    if (htmlContent.contains("<span class=\"g\">") && htmlContent.contains("</span>")) {
      String temp = htmlContent;
      int startIndex = temp.indexOf("<span class=\"g\">");
      temp = temp.substring(startIndex);
      int endIndex = temp.indexOf("</span>");
      temp = temp.substring(0, endIndex);
      photo = temp.split("&nbsp;")[1];
    }
    photo = assignDefaultValueIfBlank(photo);
    return new WebSiteDataDO("baidu", "photo", photo);
  }

}
