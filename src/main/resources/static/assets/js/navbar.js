function showLoginModal() {
    document.getElementById('loginModal').style.display = 'block';
}

function closeLoginModal() {
    document.getElementById('loginModal').style.display = 'none';
}

function login() {
    // get username and password
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    console.log('username:', username);
    console.log('password:', password);

    // use axios to send a post request to the server
    axios.post('/user/login', {
        username: username,
        password: password
    })
        .then(response => {
            // 处理登录成功
            if (response.status === 200) {
                console.log('登录成功:', response.data);
                const isAdmin = response.data.isAdmin; // 假设后端返回用户是否为管理员的信息
                const avatarUrl = response.data.avatarUrl || '/assets/images/default-avatar.png'; // 使用返回的头像路径

                // 登录成功后更新 UI
                document.getElementById('loginModal').style.display = 'none';
                document.getElementById('loginBtnContainer').style.display = 'none';
                document.getElementById('userDropdown').style.display = 'inline-block';
                document.getElementById('userName').innerText = username;
                document.getElementById('userAvatar').src = avatarUrl;

                // 隐藏 Admin Panel 按钮（如果不是管理员）
                if (!isAdmin) {
                    document.getElementById('adminPanel').style.display = 'none';
                }
            }
        })
        .catch(error => {
            // 处理登录失败的情况
            console.error('登录失败:', error);
            alert('Invalid username or password');
        });
}


function logout() {
    // 模拟登出请求（您需要替换为实际的 API 调用，例如通过 Axios 向后端发起请求）
    // 登出成功后更新 UI
    document.getElementById('userDropdown').style.display = 'none';
    document.getElementById('loginBtnContainer').style.display = 'inline-block';
    document.getElementById('adminPanel').style.display = 'block'; // 重新显示管理员按钮以备后续管理员登录
}


