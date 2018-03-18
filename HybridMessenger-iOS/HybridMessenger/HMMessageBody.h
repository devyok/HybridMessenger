//
//  HMMessageBody.h
//  hybridmessenger-sample
//
//  Created by 邓伟 on 2018/3/12.
//  Copyright © 2018年 邓伟. All rights reserved.
//

#import <Foundation/Foundation.h>

#define JSON_KEY_DATA @"data"

@interface HMMessageBody : NSObject

@property(nonatomic) NSString *data;

+(instancetype) createWithString:(NSString*) data;
+(instancetype) createWithJson:(NSString*) json;
-(instancetype) initWithData :(NSString*) data;
-(NSString*) toJson;
-(NSDictionary*) toDictionary;

@end
