//
//  HMMessage.h
//  hybridmessenger-sample
//
//  Created by 邓伟 on 2018/3/12.
//  Copyright © 2018年 邓伟. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "HybridMessenger.h"

#define JSON_KEY_HEAHER     @"header"
#define JSON_KEY_BODY       @"body"

@class HMMessageHeader;
@class HMMessageBody;

typedef NS_ENUM(NSInteger,HMMessageType) {
    MESSAGETYPE_UNKNOW = 0,
    MESSAGETYPE_NATIVE,
    MESSAGETYPE_H5
};

@interface HMMessage : NSObject

@property(nonatomic,getter=isAbort) BOOL abort;
@property(nonatomic) HMMessageHeader *header;
@property(nonatomic) HMMessageBody *body;

+(HMMessage*) createWithJson :(NSString*) json;
+(NSString*) toString :(HMMessageType) type;
+(HMMessageType) toType :(NSString*) typeString;

-(HMMessage*) reply: (HMMessage*) message;
-(HMMessage*) replyWithData: (NSString*) data;
-(NSString*) toJson;

@end
