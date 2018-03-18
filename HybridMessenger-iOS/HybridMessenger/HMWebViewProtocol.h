//
//  DWWebView.h
//  hybridmessenger-sample
//
//  Created by 邓伟 on 2018/3/12.
//  Copyright © 2018年 邓伟. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol HMWebViewProtocol <NSObject>

@required
- (NSString*) executeJavaScript: (NSString*) script;

@required
- (void) setCustomUserAgent: (NSString*) newUa;

@required
-(NSString*) getUserAgent;

@end
