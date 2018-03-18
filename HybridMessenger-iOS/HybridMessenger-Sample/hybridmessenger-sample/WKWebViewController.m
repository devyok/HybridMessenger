//
//  WKWebViewControlerViewController.m
//  hybridmessenger-sample
//
//  Created by 邓伟 on 2018/3/11.
//  Copyright © 2018年 邓伟. All rights reserved.
//

#import "WKWebViewController.h"
#import <WebKit/WebKit.h>
#import "HybridMessenger.h"
#import "HMHelper.h"
#import "HMMessage.h"
#import "HybridMessenger.h"
#import "HMMessageBody.h"
#import "HMMessageHeader.h"
#import "NSURL+HMNSURL.h"

@interface WKWebViewController ()
@property(nonatomic) WKWebView *webview;
@property(nonatomic) UIButton *runBtn;
@property(nonatomic) UIButton *runBtn2;
@property(nonatomic) NSURL *userModulWebUri;
@property(nonatomic) NSURL *productModuleWebUri;
@end

@implementation WKWebViewController

static int btnWidth = 150;
static int btnHeight = 30;
static int leftMargin = 5;

-(void)viewDidLoad {
    [super viewDidLoad];
    
    [[self navigationItem] setTitle:@"WKWebView"];
    
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
    
    [self addButtons];
    
    [self addWebView];
    
    [HybridMessenger bindWebView:self.webview];
    NSString *newUseragent = [HybridMessenger defaultCustomUserAgentString:@"xx"];
    [HybridMessenger setCustomUserAgent:newUseragent];
    
    [self loadDemoHtml];
}

-(void) addButtons {
    
    CGRect rect = [[[self navigationController] navigationBar] frame];
    
    UIButton *btn = [[UIButton alloc] initWithFrame:CGRectMake(leftMargin, rect.size.height + btnHeight, btnWidth, btnHeight)];
    self.runBtn = btn;
    btn.backgroundColor = [UIColor blackColor];
    
    [btn setTitle:@"sendH5Message" forState:UIControlStateNormal];
    [btn setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [btn setTitleColor:[UIColor redColor] forState:UIControlStateHighlighted];
    [btn addTarget:self action:@selector(sendH5MessageClick:) forControlEvents:UIControlEventTouchUpInside];
    
    [self.view addSubview:btn];
    
    UIButton *btn2 = [[UIButton alloc] initWithFrame:CGRectMake(leftMargin + btnWidth + 10, rect.size.height + btnHeight, btnWidth, btnHeight)];
    self.runBtn2 = btn2;
    btn2.backgroundColor = [UIColor blackColor];
    
    [btn2 setTitle:@"loadNewHtml" forState:UIControlStateNormal];
    [btn2 setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [btn2 setTitleColor:[UIColor redColor] forState:UIControlStateHighlighted];
    [btn2 addTarget:self action:@selector(loadNewHtml:) forControlEvents:UIControlEventTouchUpInside];
    
    [self.view addSubview:btn2];
}

-(void) addWebView {
    CGRect screenRect = [UIScreen mainScreen].bounds;
    WKWebView* webView = [[WKWebView alloc] initWithFrame:CGRectMake(0, 100,screenRect.size.width, screenRect.size.height)];
    webView.UIDelegate = self;
    self.webview = webView;
    
    [self.view addSubview:webView];
}

- (void)loadDemoHtml {
    NSString* htmlPath = [[NSBundle mainBundle] pathForResource:@"demo" ofType:@"html"];
    NSString* appHtml = [NSString stringWithContentsOfFile:htmlPath encoding:NSUTF8StringEncoding error:nil];
    NSURL *baseURL = [NSURL fileURLWithPath:htmlPath];
    [self.webview loadHTMLString:appHtml baseURL:baseURL];
}

-(void)sendH5MessageClick:(UIButton*) btn {
    
    [self sendTestMessage];
}

-(void)loadNewHtml:(UIButton*) btn {
    NSString* htmlPath = [[NSBundle mainBundle] pathForResource:@"wk_demo" ofType:@"html"];
    NSString* appHtml = [NSString stringWithContentsOfFile:htmlPath encoding:NSUTF8StringEncoding error:nil];
    NSURL *baseURL = [NSURL fileURLWithPath:htmlPath];
    [self.webview loadHTMLString:appHtml baseURL:baseURL];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void)sendTestMessage{
    
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

#pragma mark - WKUIDelegate
-(void)webView:(WKWebView *)webView runJavaScriptAlertPanelWithMessage:(NSString *)message initiatedByFrame:(WKFrameInfo *)frame completionHandler:(void (^)(void))completionHandler{

    NSString *msg = [NSString stringWithFormat:@"alert message = %@" , message];
    
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:nil message:msg delegate:self cancelButtonTitle:@"cancel" otherButtonTitles:nil, nil];
    // 因为是默认样式，所以这里可以不用写这句代码
    alert.alertViewStyle = UIAlertViewStyleDefault;
    
    [alert show];
   
    
    completionHandler();
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
