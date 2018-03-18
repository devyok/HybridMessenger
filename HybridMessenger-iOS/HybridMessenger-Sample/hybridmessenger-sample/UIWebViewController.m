//
//  ViewController.m
//  hybridmessenger-sample
//
//  Created by 邓伟 on 2018/2/6.
//  Copyright © 2018年 邓伟. All rights reserved.
//

#import "UIWebViewController.h"
#import <UIKit/UIWebView.h>
#import "UIWebView+HMUIWebView.h"
#import <WebKit/WebKit.h>
#import "HybridMessenger.h"
#import "HMMessage.h"
#import "HMMessageHeader.h"
#import "HMMessageBody.h"
#import "NSURL+HMNSURL.h"
#import "HMHelper.h"


@interface UIWebViewController ()
@property(nonatomic) UIWebView *webview;
@property(nonatomic) UIButton *runBtn;
@property(nonatomic) NSURL *userModulWebUri;
@property(nonatomic) NSURL *productModuleWebUri;

@end


@implementation UIWebViewController

static int btnWidth = 150;
static int btnHeight = 30;
static int leftMargin = 15;

- (void)viewDidLoad {
    [super viewDidLoad];
    [[[self navigationItem] leftBarButtonItem] setTitle:@"xxx"];
    [[self navigationItem] setTitle:@"UIWebView"];
}

-(void)viewDidLayoutSubviews {
    
    _userModulWebUri = [NSURL createWithModuleName:@"user"];
    _productModuleWebUri = [NSURL createWithModuleName:@"product"];
    
    [HybridMessenger registerCallbck:_userModulWebUri :^(HMMessage *message) {
        
        //        [HybridMessenger unregisterCallbck:_webUri];
        
        NSLog(@"userModule-----------, %@ " , message.body.data);
        
        //        [message replyWithData:@"hahahhahahahahahahhaha"];
        
    }];
    
    [HybridMessenger registerCallbck:_productModuleWebUri :^(HMMessage *message) {
        
        //        [HybridMessenger unregisterCallbck:_productModuleWebUri];
        
        NSLog(@"productModule-----------, %@ " , message.body.data);
        
        [message replyWithData:@"接收到产品的消息"];
        
    }];
    
    self.view.backgroundColor = [UIColor redColor];
    
    [self addNativeButton];
    
    [self addWebView];
    
    self.webview.delegate = self;
    
    NSString *newUseragent = [HybridMessenger defaultCustomUserAgentString:[self.webview getCurrentUserAgentString]];
    
    [HybridMessenger bindWebView:self.webview];
    
    [HybridMessenger setCustomUserAgent:newUseragent];
    
    [self loadDemoHtml];
    
    [HMHelper enableJavaScriptConsoleLog:self.webview];
    
}

-(void) addNativeButton {
    
    CGRect rect = [[[self navigationController] navigationBar] frame];
    
    UIButton *btn = [[UIButton alloc] initWithFrame:CGRectMake(leftMargin, rect.size.height + btnHeight, btnWidth, btnHeight)];
    self.runBtn = btn;
    btn.backgroundColor = [UIColor blackColor];
    
    [btn setTitle:@"sendH5Message" forState:UIControlStateNormal];
    [btn setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [btn setTitleColor:[UIColor redColor] forState:UIControlStateHighlighted];
    [btn addTarget:self action:@selector(sendH5MessageClick:) forControlEvents:UIControlEventTouchUpInside];
    
    [self.view addSubview:btn];
}

-(void) addWebView {
    CGRect rect = [[[self navigationController] navigationBar] frame];
    CGRect screenRect = [UIScreen mainScreen].bounds;
    UIWebView *webview = [[UIWebView alloc] initWithFrame:CGRectMake(0, rect.size.height + 2*btnHeight + 10,screenRect.size.width, screenRect.size.height)];
    
    self.webview = webview;
    
    [self.view addSubview:self.webview];
}

-(void) loadDemoHtml {
    NSString *path = [[NSBundle mainBundle] pathForResource:@"demo" ofType:@"html"];
    
    NSURL *url = [[NSURL alloc] initFileURLWithPath:path];
    
    NSURLRequest *request = [[NSURLRequest alloc] initWithURL:url];
    
    [self.webview loadRequest:request];
}



-(void) sendTestMessage{
    
    NSURL *webUri = _userModulWebUri;
    
    HMMessage *message = [[HMMessage alloc] init];
    
    HMMessageHeader *header = [[HMMessageHeader alloc] init];
    
    header.messageType = MESSAGETYPE_NATIVE;
    header.from = @"native";
    header.webUrl = webUri;
    
    HMMessageBody *body = [[HMMessageBody alloc] init];
    body.data = @"iOS Native Hybrid Message";
    
    message.header = header;
    message.body = body;
    
    NSString *json = [message toJson];
    
    NSLog(@"message json = %@", json);

    
//    [[HybridMessenger sharedInstance] sendHybridMessage:message :^(HMMessage *message) {
//        NSLog(@"bbbbbbbbbbbbbbb");
//    }];

    [HybridMessenger sendHybridMessage:message];
    
}

-(void) nsurlTester{
    NSURL *url1 = [[NSURL alloc] initWithString:@"http://www.baidu.com:80/a?key1=value1&key2=value2"];
    
    NSURL *url2 = [[NSURL alloc] initWithString:@"HybridMessage://devyok:80"];
    
    [url1 outputConsole];
    [url2 outputConsole];
    
    NSString *result = [url1 getQueryParameter:@"key1"];
    NSString *result2 = [url1 getQueryParameter:@"key2"];
    
    NSLog(@"url1 getQueryParameter = %@ , result2 = %@" , result,result2);
}

-(void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event{
    
    NSLog(@"touch enter");
    
}

-(void)sendH5MessageClick:(UIButton*) btn {
    
    [self sendTestMessage];
    
}

-(BOOL)webView:(UIWebView *)webView shouldStartLoadWithRequest:(NSURLRequest *)request navigationType:(UIWebViewNavigationType)navigationType {
    
    NSLog(@"user shouldStartLoadWithRequest");
    
    return TRUE;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}


@end
