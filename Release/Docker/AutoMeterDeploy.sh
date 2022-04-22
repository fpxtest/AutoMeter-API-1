#!/bin/sh
 function getIpAddr() 
 {
        # 获取IP命令
        echo "开始获取ip"
        ipaddr=`ifconfig -a|grep inet|grep -v 192.168.3.95|grep -v inet6|awk '{print $2}'|tr -d "addr:"`
        echo "获取ip：$ipaddr"
        array=(`echo $ipaddr | tr '\n' ' '` )  # IP地址分割，区分是否多网卡
        #array=(172.20.32.214 192.168.1.10 192.168.1.2 192.168.1.10 192.168.1.2 192.168.1.10 192.168.1.2 192.168.1.10 192.168.1.2 192.168.1.10 192.168.1.2);
        num=${#array[@]}                                                #获取数组元素的个数
        echo "ip数组长度为$num"

        # 选择安装的IP地址
        if [ $num -eq 1 ]; then
                echo "*单网卡"
                local_ip=${array[*]}
        elif [ $num -gt 1 ];then
                echo -e "\033[035m******************************\033[0m"
                echo -e "\033[036m*    请选择安装的IP地址               \033[0m"
                for ((i=0; i<=$num-1;i++))
                do
                        #echo $num
                        echo -e "\033[032m*     $i : ${array[$i]}                \033[0m"
                done    
                #echo -e "\033[032m*      1 : ${array[0]}               \033[0m"
                #echo -e "\033[034m*      2 : ${array[1]}               \033[0m"
                echo -e "\033[035m******************************\033[0m"
                #选择需要安装的服务类型
                input=""
                while :
                do
                        read -r -p "*请选择安装的IP地址(序号): " input
                        case $input in
                           [0-9]*)
                                        local_ip=${array[$input]}
                                        #echo "选择网段1的IP为：${local_ip}"
                                        break
                                        ;;
                                *)
                                echo "*请输入有效的数字:"
                                        ;;
                        esac
                done
        else
                echo -e "\033[31m*未设置网卡IP，请检查服务器环境！ \033[0m"
                exit 1
        fi
} 

# 校验IP地址合法性
function isValidIp() 
{
        local ip=$1
        local ret=1
 
        if [[ $ip =~ ^[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}$ ]]; then
                ip=(${ip//\./ }) # 按.分割，转成数组，方便下面的判断
                [[ ${ip[0]} -le 255 && ${ip[1]} -le 255 && ${ip[2]} -le 255 && ${ip[3]} -le 255 ]]
                ret=$?
        fi
        return $ret
}
 
local_ip=''
echo "开始getIpAddr"
getIpAddr       #自动获取IP
echo "结束getIpAddr"
isValidIp ${local_ip}   # IP校验
if [ $? -ne 0 ]; then
        echo -e "\033[31m*自动获取的IP地址无效，请重试！ \033[0m"
        exit 1
fi
echo "*选择安装的IP地址为：${local_ip}"

sed -i  "s@192.168.3.95@${local_ip}@" ../Beta/conditionservice/config/application.yml
sed -i  "s@192.168.3.95@${local_ip}@" ../Beta/dispatchservice/config/application.yml 
sed -i  "s@192.168.3.95@${local_ip}@" ../Beta/slaverservice/config/application.yml 
sed -i  "s@192.168.3.95@${local_ip}@" ../Beta/testcenterservice/config/application.yml 
sed -i  "s@192.168.3.95@${local_ip}@" ../Beta/testcenterapp/dist/static/config.js
echo "修改IP成功"
docker-compose -f docker-compose.yaml up -d 
echo "AutoMeter部署成功，执行完增量sql后，访问入口 http://$local_ip:8084  默认账户密码admin admin123"