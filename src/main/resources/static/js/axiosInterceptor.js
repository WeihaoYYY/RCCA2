
// 创建 axios 实例
const axiosInstance = axios.create();

// 设置请求拦截器
axiosInstance.interceptors.request.use(config => {
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
