FROM nginx:1.19.6
USER root
COPY dist/ /usr/share/nginx/html
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone \
 && sed -i s@/deb.debian.org/@/mirrors.aliyun.com/@g /etc/apt/sources.list \
 && apt-get clean \
 && apt-get update -y \
 && apt-get install vim -y
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
