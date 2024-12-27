const axiosInstance = axios.create();

const hostname = window.location.hostname;
const isProduction = hostname !== 'localhost' && hostname !== '127.0.0.1';

// 设置请求拦截器
axiosInstance.interceptors.request.use(config => {
    // 添加环境判断，根据环境添加前缀
    if (isProduction) {
        // 如果请求的URL不以'/api'开头，则添加'/api'前缀
        if (!config.url.startsWith('/api')) {
            config.url = '/api' + config.url;
        }
    }

    // 处理Token
    const token = localStorage.getItem('jwtToken');
    if (token) {
        config.headers['Authorization'] = `Bearer ${token}`;
        config.headers['token'] = token; // 添加自定义的 token 头
    }

    return config;
}, error => {
    return Promise.reject(error);
});

// 默认导出 axios 实例
export default axiosInstance;
