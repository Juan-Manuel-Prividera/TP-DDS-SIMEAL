function togglePw(inputId, inlineClass) {
    const passwordInput = document.getElementById(inputId);
    const togglePassword = document.querySelector(inlineClass);

    if (passwordInput.type === 'text') {
        passwordInput.type = 'password';
        togglePassword.classList.remove("fa-eye");
        togglePassword.classList.add("fa-eye-slash");
    } else {
        passwordInput.type = 'text';
        togglePassword.classList.remove("fa-eye-slash");
        togglePassword.classList.add("fa-eye");
    }
}

