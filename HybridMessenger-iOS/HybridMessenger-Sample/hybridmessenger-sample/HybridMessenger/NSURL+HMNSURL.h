//
//  NSURL+HMNSURL.h
//  hybridmessenger-sample
//
//  Created by 邓伟 on 2018/3/14.
//  Copyright © 2018年 邓伟. All rights reserved.
//

#import <Foundation/Foundation.h>

#define JSON_KEY_SCHEME         @"scheme"
#define JSON_KEY_HOST           @"host"
#define JSON_KEY_PORT           @"port"
#define JSON_KEY_PATH           @"path"
#define JSON_KEY_QUERY          @"query"
#define JSON_KEY_QUERY_VALUE    @"queryValue"
#define DEFAULT_URL             @"HybridMessage://devyok:80"


@interface NSURL (HMNSURL)

+(NSURL*) createWithJson: (NSString*) json;
+(NSURL*) createWithModuleName :(NSString*) modulName;

-(NSURL*) getPathUrl;
-(NSString*) getQueryParameter: (NSString*) key;
-(NSArray*) getQueryParameterNames;
-(NSMutableDictionary*) getQueryParameterDictionary;
-(NSString*) toUrlString;
-(NSString*) toJson;
-(NSDictionary*) toDictionary;

-(void) outputConsole;

@end
