//
//  HybridMessenger.m
//  hybridmessenger-sample
//
//  Created by 邓伟 on 2018/3/12.
//  Copyright © 2018年 邓伟. All rights reserved.
//

#import "HybridMessenger.h"
#import <WebKit/WebKit.h>
#import "HMUIWebViewProtocolImpl.h"
#import "HMWKWebViewProtocolImpl.h"
#import "HMException.h"
#import "HMMessage.h"
#import "HMMessageHeader.h"
#import "NSURL+HMNSURL.h"


@interface HybridMessenger ()
@property(nonatomic) id<HMWebViewProtocol> impl;
@property(nonatomic) NSMutableDictionary *idCallbacks;
@property(nonatomic) NSMutableDictionary *urlCallbacks;
@end

@implementation HybridMessenger

static HybridMessenger *_instance = nil;
static id<HMWebViewProtocol> impl;

+(instancetype)sharedInstance {
    static dispatch_once_t onceToken ;
    dispatch_once(&onceToken, ^{
        _instance = [[super allocWithZone:NULL] init] ;
    }) ;
    
    return _instance ;
}

-(NSMutableDictionary*) getIdCallbacks {
    if(_idCallbacks==nil){
        _idCallbacks = [[NSMutableDictionary alloc] init];
    }
    return _idCallbacks;
}

-(NSMutableDictionary*) getUrlCallbacks {
    if(_urlCallbacks==nil){
        _urlCallbacks = [[NSMutableDictionary alloc] init];
    }
    return _urlCallbacks;
}

+(id) allocWithZone:(struct _NSZone *)zone
{
    return [HybridMessenger sharedInstance] ;
}

-(id) copyWithZone:(struct _NSZone *)zone
{
    return [HybridMessenger sharedInstance] ;
}

+(void) check{
    if([HybridMessenger sharedInstance].impl == nil){
        @throw [HMException createWebViewNotBindException:@"not bind web view"];
    }
}

+(HybridMessenger*)bindWebView:(id)webview {
    
    if(webview == nil){
        @throw [HMException createIllegalArgumentException:@"arg webview is null"];
    }
    
    HybridMessenger *messenger = [HybridMessenger sharedInstance];
    
    if([webview isKindOfClass:[UIWebView class]]) {
        messenger.impl = [[HMUIWebViewProtocolImpl alloc] initWithUIWebView:(UIWebView*) webview];
    } else {
        messenger.impl = [[HMWKWebViewProtocolImpl alloc] initWithWKWebView:(WKWebView*) webview];
    }
    
    return messenger;
}

+(BOOL)dispatch:(HMMessage *)message {
    
    [HybridMessenger check];
    
    [[HybridMessenger sharedInstance] dispatchIdCallabck:message];
    [[HybridMessenger sharedInstance] dispatchUrlCallabck:message];
    
    return TRUE;
    
}

+(HybridMessenger*)registerCallbck:(NSURL *)url :(HybridMessageCallback)callback {
    
    if(url == nil){
        @throw [HMException createIllegalArgumentException:@"arg url is null"];
    }
    
    HybridMessenger *messenger = [HybridMessenger sharedInstance];
    NSString *urlString = [[url getPathUrl] absoluteString];
    [[messenger getUrlCallbacks] setObject:callback forKey:urlString];
    
    return messenger;
}

+(HybridMessenger*)unregisterCallbck:(NSURL *)url {
    HybridMessenger *messenger = [HybridMessenger sharedInstance];
    NSString *urlString = [[url getPathUrl] absoluteString];
    
    [[messenger getUrlCallbacks] removeObjectForKey:urlString];
    
    return messenger;
}

+(void)sendHybridMessage:(HMMessage *)message {
    
    [HybridMessenger check];
    
    HybridMessenger *messenger = [HybridMessenger sharedInstance];
    
    NSString *cmd = [message toJson];
    
    NSString *javascriptcmd = [NSString stringWithFormat:@"window.HybridMessenger.onReceiveHybridMessage('%@');",cmd];
    
    NSLog(@"HybridMessenger sendHybridMessage javascriptcmd = %@" , javascriptcmd);
    
    [messenger executeJavaScript:javascriptcmd];
}

+(void)sendHybridMessage:(HMMessage *)message :(HybridMessageCallback)callback {
    
    [HybridMessenger check];
    
    HybridMessenger *messenger = [HybridMessenger sharedInstance];
    NSMutableDictionary *callbacks = [messenger getIdCallbacks];
    
    NSString *messageId = message.header.messageId;
    
    [callbacks setObject:callback forKey:messageId];
    
    [HybridMessenger sendHybridMessage:message];
    
}

+(void)setCustomUserAgent:(NSString *)newUa {
    [HybridMessenger check];
    [[HybridMessenger sharedInstance].impl setCustomUserAgent:newUa];
}

-(NSString *)getUserAgent {
    return [[HybridMessenger sharedInstance].impl getUserAgent];
}

-(NSString*)executeJavaScript:(NSString *)script {
    return [[HybridMessenger sharedInstance].impl executeJavaScript:script];
}

+ (NSString *)defaultCustomUserAgentString:(NSString *)oldUa {
    
    NSString *version = [[[NSBundle mainBundle] infoDictionary] objectForKey:@"CFBundleShortVersionString"];
    NSString *package = [[[NSBundle mainBundle] infoDictionary] objectForKey:@"CFBundleIdentifier"];
    
    NSDictionary *newUseragent = @{@"version":version,@"packageName":package,@"platform":@"iOS",@"defaultUserAgent":oldUa
                                   ,@"extInfos":@""
                                   };
    
    NSData *newUseragentJsonData = [NSJSONSerialization dataWithJSONObject:newUseragent options:NSJSONWritingPrettyPrinted error:NULL];
    
    NSString *jsonUseragent = [[NSString alloc] initWithData:newUseragentJsonData encoding:NSUTF8StringEncoding];
    
    return jsonUseragent;
}

-(BOOL) dispatchIdCallabck: (HMMessage*) message {
    
    NSString *messageId = message.header.messageId;
    NSMutableDictionary *callbacks = [[HybridMessenger sharedInstance] getIdCallbacks];
    
    HybridMessageCallback callback = [callbacks objectForKey:messageId];
    if(callback){
        callback(message);
        [callbacks removeObjectForKey:messageId];
    }
    return TRUE;
}

-(BOOL) dispatchUrlCallabck: (HMMessage*) message {
    NSURL *url = [message.header.webUrl getPathUrl];
    NSMutableDictionary *callbacks = [[HybridMessenger sharedInstance] getUrlCallbacks];
    
    HybridMessageCallback callback = [callbacks objectForKey:[url absoluteString]];
    if(callback){
        callback(message);
    }
    return TRUE;
}

@end
