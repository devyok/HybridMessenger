//
//  MainViewController.m
//  hybridmessenger-sample
//
//  Created by 邓伟 on 2018/3/17.
//  Copyright © 2018年 邓伟. All rights reserved.
//

#import "MainViewController.h"
#import "UIWebViewController.h"
#import "WKWebViewController.h"

@interface MainViewController ()

@end

@implementation MainViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    // Do any additional setup after loading the view.
//    [self addButtons];
}
-(void)viewWillAppear:(BOOL)animated {
    
}

-(void)viewDidLayoutSubviews {
    [self addButtons];
}

-(void) addButtons {
    
    CGRect rect = [[[self navigationController] navigationBar] frame];
    
    
    NSLog(@"rect.size.height = %f",rect.size.height);
    
    int btnWidth = 150;
    int btnHeight = 30;
    int leftMargin = 15;
    
    
    UIButton *btn = [[UIButton alloc] initWithFrame:CGRectMake(leftMargin, rect.size.height + btnHeight, btnWidth, btnHeight)];
    
    btn.backgroundColor = [UIColor blackColor];
    
    [btn setTitle:@"UIWebView Test" forState:UIControlStateNormal];
    [btn setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [btn setTitleColor:[UIColor redColor] forState:UIControlStateHighlighted];
    [btn addTarget:self action:@selector(startUIWebViewControler) forControlEvents:UIControlEventTouchUpInside];
    
    [self.view addSubview:btn];
    
    int marginTop = 10;
    UIButton *btn2 = [[UIButton alloc] initWithFrame:CGRectMake(leftMargin, rect.size.height + 2 * btnHeight + marginTop, btnWidth, btnHeight)];
    
    btn2.backgroundColor = [UIColor blackColor];
    
    [btn2 setTitle:@"WKWebView Test" forState:UIControlStateNormal];
    [btn2 setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [btn2 setTitleColor:[UIColor redColor] forState:UIControlStateHighlighted];
    [btn2 addTarget:self action:@selector(startWKWebViewControler) forControlEvents:UIControlEventTouchUpInside];
    
    [self.view addSubview:btn2];
}


-(void) startUIWebViewControler {
    
    UIWebViewController *controler = [[UIWebViewController alloc] init];
    [[self navigationController] pushViewController:controler animated:true];
}

-(void) startWKWebViewControler {
    
    WKWebViewController *controler = [[WKWebViewController alloc] init];
    [[self navigationController] pushViewController:controler animated:true];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
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
