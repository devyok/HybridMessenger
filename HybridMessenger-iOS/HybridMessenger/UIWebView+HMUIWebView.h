//
//  UIWebView+DWUIWebView.h
//  hybridmessenger-sample
//
//  Created by 邓伟 on 2018/3/11.
//  Copyright © 2018年 邓伟. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface UIWebView (HMUIWebView)
-(void) setCustomUserAgent :(NSString*) newUserAgent;

-(NSString*) getCurrentUserAgentString;
@end
