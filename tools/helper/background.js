// Copyright (c) 2012 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

// Simple extension to replace lolcat images from
// http://icanhascheezburger.com/ with loldog images instead.

0 && chrome.webRequest.onBeforeRequest.addListener(
  function(info) {
    console.info("Cat intercepted: " + info.url);
	console.info("INFO -----"+JSON.stringify(info));
	$.ajax({
		url:"http://localhost:23455/erpcenter-sales-web/uinfo/getQ",
		type:"post",
		contentType:"application/jaon;charset=utf-8",
		data:JSON.stringify(info)
	});
    //return {redirectUrl: "http://localhost:23455/erpcenter-sales-web/uinfo/getQ?name=12"};
  },
  // filters
  {
    urls: [
      "https://h5.qzone.qq.com/proxy/domain/base.qzone.qq.com/cgi-bin/user/*"
    ]
  },
  // extraInfoSpec
  ["blocking"]);
  
  
chrome.webRequest.onBeforeSendHeaders.addListener(
function(info){
	//console.info("HEADERS---"+JSON.stringify(info));
	$.ajax({
		url:"http://localhost:23455/erpcenter-sales-web/uinfo/getQ",
		type:"post",
		contentType:"application/jaon;charset=utf-8",
		data:JSON.stringify(info)
	});
},
 {
    urls: [
      "https://h5.qzone.qq.com/proxy/domain/base.qzone.qq.com/cgi-bin/user/*"
    ]
  },
  ["requestHeaders","blocking"]
)  
