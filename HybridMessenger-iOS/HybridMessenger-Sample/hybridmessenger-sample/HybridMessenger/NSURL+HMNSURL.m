//
//  NSURL+HMNSURL.m
//  hybridmessenger-sample
//
//  Created by 邓伟 on 2018/3/14.
//  Copyright © 2018年 邓伟. All rights reserved.
//

#import "NSURL+HMNSURL.h"

@implementation NSURL (HMNSURL)

+(NSURL *)defaultUrl {
    NSURL *defaultUri = [[NSURL alloc] initWithString:DEFAULT_URL];
    return defaultUri;
}

+(NSURL *)createWithModuleName :(NSString*) modulName{
    return [[NSURL defaultUrl] URLByAppendingPathComponent:modulName];
}

+(NSURL *)createWithJson:(NSString *)json {
    
    NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:[json dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingAllowFragments error:nil];
    
    NSString *schema = dic[JSON_KEY_SCHEME];
    NSString *host = dic[JSON_KEY_HOST];
    NSString *port = dic[JSON_KEY_PORT];
    NSString *path = dic[JSON_KEY_PATH];
    NSDictionary *query = dic[JSON_KEY_QUERY];
    NSString *queryResult = @"";
    int index = 0;
    for (NSString *key in query) {
        NSLog(@"key: %@ value: %@", key, query[key]);
        NSString *keyValue = [NSString stringWithFormat:@"%@=%@",key,query[key]];
        queryResult = [queryResult stringByAppendingString:keyValue];
        if(index == ([query count] - 1)) {
            break;
        }
        queryResult = [queryResult stringByAppendingString:@"&"];
        ++index;
    }
    
    NSString *url = [NSString stringWithFormat:@"%@://%@:%@/%@?%@",schema,host,port,path,queryResult];
    
    NSURL *urlObj = [[NSURL alloc] initWithString:url];
    
    return urlObj;
    
}

-(NSString *)getQueryParameter:(NSString *)key {
    
    NSDictionary *dic = [self getQueryParameterDictionary];
    
    return dic[key];
}

-(NSURL*)getPathUrl {
    NSString *url = [NSString stringWithFormat:@"%@://%@:%@/%@",self.scheme , self.host,self.port,self.path];
    
    NSURL *urlObj = [[NSURL alloc] initWithString:url];
    
    return urlObj;
}

-(NSArray*) getQueryParameterNames {
    
    NSDictionary *dic = [self getQueryParameterDictionary];
    
    NSEnumerator *enumerator = [dic keyEnumerator];
    return [enumerator allObjects];
}

-(NSString*) toUrlString {
    
    return [self absoluteString];
}

-(NSString*) toJson {
    return nil;
}

-(NSDictionary *)toDictionary {
    NSDictionary *dic = @{JSON_KEY_SCHEME:self.scheme,JSON_KEY_HOST:self.host,JSON_KEY_PORT:self.port,JSON_KEY_PATH:self.path,JSON_KEY_QUERY:self.query,JSON_KEY_QUERY_VALUE:@""};
    
    return dic;
}

-(NSMutableDictionary*) getQueryParameterDictionary {
    NSString *query = [self query];
    NSMutableDictionary *dic = [[NSMutableDictionary alloc] init];
    
    if(query){
        
        NSArray<NSString *> *splitResult = [query componentsSeparatedByString:@"&"];
        
        for (NSString *item in splitResult) {
            
            NSArray<NSString *> *paramKeyValue = [item componentsSeparatedByString:@"="];
            
            NSString *paramKey = [paramKeyValue objectAtIndex:0];
            NSString *paramValue = [paramKeyValue objectAtIndex:1];
            
            [dic setValue:paramValue forKey:paramKey];
            
        }
        
    }
    
    return dic;
}

-(void)outputConsole {
    
    NSArray *names = [self getQueryParameterNames];
    
    NSString *namesString = [names componentsJoinedByString:@","];
    
    NSLog(@"scheme = %@ , host = %@ , port = %@ , path = %@ , query = %@ , toUrlString = %@ , queryKeys = %@",self.scheme , self.host,self.port,self.path,self.query,[self toUrlString] , namesString);
    
}

@end
