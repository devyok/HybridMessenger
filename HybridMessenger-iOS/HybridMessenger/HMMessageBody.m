//
//  HMMessageBody.m
//  hybridmessenger-sample
//
//  Created by 邓伟 on 2018/3/12.
//  Copyright © 2018年 邓伟. All rights reserved.
//

#import "HMMessageBody.h"
#import "HMHelper.h"

@implementation HMMessageBody

+(instancetype)createWithString:(NSString *)data {
    return [[HMMessageBody alloc] initWithData:data];
}

+(instancetype)createWithJson:(NSString *)json {
    
    NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:[json dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil];
    
    return [HMMessageBody createWithString:dic[JSON_KEY_DATA]];
}

-(instancetype)initWithData:(NSString *)data {
    if(self = [super init]) {
        _data = data;
    }
    return self;
}

-(NSString *)toJson {
    
    return [HMHelper toJson:[self toDictionary]];
}

-(NSDictionary*)toDictionary {
    NSDictionary *jsonDic = @{JSON_KEY_DATA:self.data};
    return jsonDic;
}

-(NSString *)description {
    return [NSString stringWithFormat:@"[body.data = %@]",self.data];
}

@end
