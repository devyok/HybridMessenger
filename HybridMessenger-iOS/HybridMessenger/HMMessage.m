//
//  HMMessage.m
//  hybridmessenger-sample
//
//  Created by 邓伟 on 2018/3/12.
//  Copyright © 2018年 邓伟. All rights reserved.
//

#import "HMMessage.h"
#import "HMMessageHeader.h"
#import "HMMessageBody.h"
#import "HMHelper.h"

@implementation HMMessage

+(HMMessage *)createWithJson:(NSString *)json {
    
    HMMessage *message = [[HMMessage alloc] init];
    
    NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:[json dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil];
    
    NSDictionary *headerDic = [dic objectForKey:JSON_KEY_HEAHER];
    NSDictionary *bodyDic = [dic objectForKey:JSON_KEY_BODY];
    
    HMMessageHeader *header = [HMMessageHeader createWithJson:[HMHelper toJson:headerDic]];
    HMMessageBody *body = [HMMessageBody createWithJson:[HMHelper toJson:bodyDic]];
    
    message.header = header;
    message.body = body;
    
    return message;
    
}

+(NSString *)toString:(HMMessageType)type{
    
    if(type == MESSAGETYPE_NATIVE){
        return @"native";
    } else if(type == MESSAGETYPE_H5){
        return @"h5";
    } else {
        return @"unknow";
    }
    
}

-(HMMessage*)reply:(HMMessage *)message {
    
    [HybridMessenger sendHybridMessage:message];
    
    return self;
}

-(HMMessage*) replyWithData:(NSString *)data {
    
    self.body.data = data;
    
    [HybridMessenger sendHybridMessage:self];
    
    return self;
}

+(HMMessageType)toType:(NSString *)typeString{
    if([typeString isEqualToString:@"native"]){
        return MESSAGETYPE_NATIVE;
    } else if([typeString isEqualToString:@"h5"]){
        return MESSAGETYPE_H5;
    } else {
        return MESSAGETYPE_UNKNOW;
    }
}

-(NSString *)toJson {
    
    NSDictionary *headerDic = [self.header toDictionary];
    NSDictionary *bodyDic = [self.body toDictionary];
    
    NSDictionary *jsonDic = @{JSON_KEY_HEAHER:headerDic,JSON_KEY_BODY:bodyDic};
    
    return [HMHelper toJson:jsonDic];
    
}

-(NSString *)description {
    return [NSString stringWithFormat:@"{header = [%@] , body = %@]}",self.header,self.body];
}

@end
