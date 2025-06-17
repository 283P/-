// 切换登录/注册表单
document.querySelectorAll('.switch-btn').forEach(btn => {
    btn.addEventListener('click', (e) => {
        e.preventDefault();
        const target = btn.dataset.target;
        
        // 反转逻辑：点击注册显示登录，点击登录显示注册
        const showLogin = target === 'register';
        
        document.querySelectorAll('.form-panel').forEach(panel => {
            panel.classList.remove('active');
        });
        
        // 统一使用login-form/register-form类名
        const activePanel = showLogin 
            ? document.querySelector('.login-form')
            : document.querySelector('.register-form');
            
        activePanel.classList.add('active');
        
        // 直接控制覆盖层位置
        const overlay = document.querySelector('.slider-overlay');
        overlay.style.transform = showLogin 
            ? 'translateX(0)' 
            : 'translateX(100%)';
    });
});
 document.getElementById('registerForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const form = e.target;
        const formData = new FormData(form);
        
        if(formData.get('password') !== formData.get('confirmPassword')) {
            alert('两次输入的密码不一致');
            return;
        }

        try {
            const response = await fetch(form.action, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    username: formData.get('username'),
                    password: formData.get('password')
                })
            });

            if(response.ok) {
                alert('注册成功');
                // 注册成功后自动切换到登录表单
                document.querySelector('.login-form').classList.add('active');
                document.querySelector('.register-form').classList.remove('active');
                document.querySelector('.slider-overlay').style.transform = 'translateX(0)';
            } else {
                const data = await response.json();
                alert(data.message || '注册失败');
            }
        } catch (error) {
            console.error('注册错误:', error);
            alert('网络错误，请稍后再试');
        }
    });