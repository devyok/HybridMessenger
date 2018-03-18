//
//  HMMessageHeader.m
//  hybridmessenger-sample
//
//  Created by 邓伟 on 2018/3/12.
//  Copyright © 2018年 邓伟. All rights reserved.
//

#import "HMMessageHeader.h"
#import "NSURL+HMNSURL.h"
#import "HMHelper.h"

@implementation HMMessageHeader

+(instancetype)createWithJson:(NSString *)json{
    
    
    NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:[json dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil];
    
    NSString *from = [dic objectForKey:JSON_KEY_FROM];
    NSString *messageId = [dic objectForKey:JSON_KEY_MESSAGE_ID];
    HMMessageType messageType = [HMMessage toType:[dic objectForKey:JSON_KEY_TYPE]];
    
    NSDictionary *uriDic = [dic objectForKey:JSON_KEY_URI];
    
    NSURL *webUrl = [NSURL createWithJson:[HMHelper toJson:uriDic]];
    
    HMMessageHeader *header = [[HMMessageHeader alloc] initWithMessageId:messageId];
    header.from = from;
    header.messageType = messageType;
    header.webUrl = webUrl;
    
    
    return header;
}

- (instancetype)init {
    return [self initWithMessageId:[HMHelper makeUUID]];
}

-(instancetype)initWithMessageId:(NSString *)messageId {
    self = [super init];
    if (self) {
        _messageId = messageId;
    }
    return self;
}

-(NSString *)toJson {
    return [HMHelper toJson:[self toDictionary]];
}

-(NSDictionary *)toDictionary {
    NSDictionary *jsonDic = @{JSON_KEY_MESSAGE_ID:self.messageId,JSON_KEY_FROM:self.from,JSON_KEY_URI:[[self webUrl] toUrlString],JSON_KEY_TYPE:[HMMessage toString:self.messageType]};
    
    return jsonDic;
}

-(NSString *)description {
    return [NSString stringWithFormat:@"[header.id = %@ , header.from = %@ , header.type = %@ , header.url = %@]",self.messageId,self.from,[HMMessage toString:self.messageType],[self.webUrl toUrlString]];
}

@end
