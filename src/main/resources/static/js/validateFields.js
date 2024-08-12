// Todo chequear el resto de campos
function validateFields() {
    const password = document.getElementById('password').value;
    const passwordRepeat = document.getElementById('passwordRepeat').value;

    if (password !== passwordRepeat) {
    alert("Las contraseñas no coinciden. Por favor, intente nuevamente.");
    return false; // Prevent form submission
    }
    return true; // Allow form submission
}