//
//  HMMessageHeader.h
//  hybridmessenger-sample
//
//  Created by 邓伟 on 2018/3/12.
//  Copyright © 2018年 邓伟. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "HMMessage.h"


#define JSON_KEY_MESSAGE_ID @"messageId"
#define JSON_KEY_TYPE       @"type"
#define JSON_KEY_FROM       @"from"
#define JSON_KEY_URI        @"uri"

@interface HMMessageHeader : NSObject

@property(nonatomic,readonly) NSString *messageId;
@property(nonatomic) NSString *from;
@property(nonatomic) NSURL *webUrl;
@property(nonatomic,assign) HMMessageType messageType;

+(instancetype) createWithJson:(NSString*) json;
-(instancetype) initWithMessageId :(NSString*) messageId;
-(NSString*) toJson;
-(NSDictionary*) toDictionary;

@end
