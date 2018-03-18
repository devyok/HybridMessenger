//
//  HMWebViewNotBindException.h
//  hybridmessenger-sample
//
//  Created by 邓伟 on 2018/3/14.
//  Copyright © 2018年 邓伟. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface HMException : NSException

+(HMException*) createIllegalArgumentException :(NSString*) reason;

+(HMException*) createWebViewNotBindException :(NSString*) reason;

@end
