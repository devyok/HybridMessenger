//
//  HMLogger.h
//  hybridmessenger-sample
//
//  Created by 邓伟 on 2018/2/6.
//  Copyright © 2018年 邓伟. All rights reserved.
//

#ifndef HMLogger_h
#define HMLogger_h

#define __FILENAME__ (strrchr(__FILE__,'/')+1)
#define __DEBUG_INFOS__ ([NSString stringWithFormat:@"{%s %s}",__FILENAME__,__func__])

#define LOG_INFO(message,...) NSLog(message,##__VA_ARGS__)

#ifdef DEBUG
#define LOG_DEBUG(message,...) NSLog(@"%@",[[NSString stringWithFormat:@"%@ " , __DEBUG_INFOS__] \
    stringByAppendingFormat:message,##__VA_ARGS__]); \

#else
#define LOG_DEBUG(message,...)
#endif

#endif /* HMLogger_h */
