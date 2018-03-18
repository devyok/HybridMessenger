//
//  HMWKWebViewImpl.h
//  hybridmessenger-sample
//
//  Created by 邓伟 on 2018/3/12.
//  Copyright © 2018年 邓伟. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <WebKit/WebKit.h>
#import "HMWebViewBaseImpl.h"

@interface HMWKWebViewProtocolImpl : HMWebViewBaseImpl<WKNavigationDelegate,WKUIDelegate>
-(instancetype)initWithWKWebView :(WKWebView*) wkwebview;
@end
