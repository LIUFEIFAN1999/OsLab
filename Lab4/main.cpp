//
//  main.cpp
//  fork
//
//  Created by a on 2021/5/20.
//

#include <iostream>
#include<unistd.h>
using namespace std;

int main(int argc, const char * argv[]) {
    int n = 10;
    int pid1,pid2,pid3;
    cout<<getpid()<<"before"<<endl;
    pid1 = fork();
    wait(NULL);
    cout<<getpid()<<"after"<<endl;
    if(pid1==0){
        n=n-1;
        cout<<"子进程"<<getpid()<<"执行..."<<" 父进程"<<getppid()<<" n="<<n<<endl;
    }
    if(pid1<0){
        cout<<"创建失败"<<endl;
    }
    pid2 = fork();
    if(pid2==0){
        n=n-1;
        cout<<"子进程"<<getpid()<<"执行..."<<" 父进程"<<getppid()<<" n="<<n<<endl;
    }
    if(pid2<0){
        cout<<"创建失败"<<endl;
    }
    pid3 = fork();
    if(pid3==0){
        n=n-1;
        cout<<"子进程"<<getpid()<<"执行..."<<" 父进程"<<getppid()<<" n="<<n<<endl;
    }
    if(pid3<0){
        cout<<"创建失败"<<endl;
    }
    sleep(2);
    return 0;
}
