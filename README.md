# GXMusic
基于kotlin+Jetpack的开源音乐APP

后端地址 https://github.com/Binaryify/NeteaseCloudMusicApi.git


使用：
1. 启动后端接口
2. 使用内网穿透或其他办法将localhost转换为公网地址
 ![image](https://github.com/Gashi-JR/GXMusic/assets/130421716/344a3397-4b61-4aad-b08d-48180f1d721f)
3. 将公网地址替换http.kt里面的基本URL
  ![image](https://github.com/Gashi-JR/GXMusic/assets/130421716/12775abf-d0a8-4bdb-be1d-769c46edcb7b)
4. 编译运行

![image](https://github.com/Gashi-JR/GXMusic/assets/130421716/d2c491b1-e210-4ddb-8c62-0d0b2004e28e)

 
  
  注意：若需登录，请使用网易云音乐app扫码登录,注意别登录多了，会被网易云封ip.
  
  不想登录，就把MainActivity.kt里面的isLogin状态的初值改为false
      ![image](https://github.com/Gashi-JR/GXMusic/assets/130421716/67023495-b823-420f-9c08-2a8f3d67555a)
