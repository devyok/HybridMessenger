//
//  HMWebViewNotBindException.m
//  hybridmessenger-sample
//
//  Created by 邓伟 on 2018/3/14.
//  Copyright © 2018年 邓伟. All rights reserved.
//

#import "HMException.h"

@implementation HMException

+(HMException *)createIllegalArgumentException:(NSString *)reason {
    return [[HMException alloc] initWithName:@"IllegalArgumentException" reason:reason userInfo:nil];
}

+(HMException *)createWebViewNotBindException:(NSString *)reason {
    return [[HMException alloc] initWithName:@"WebViewNotBindException" reason:reason userInfo:nil];
}

@end
