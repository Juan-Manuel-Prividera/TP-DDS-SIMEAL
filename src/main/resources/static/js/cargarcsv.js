document.getElementById('uploadForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const fileInput = document.getElementById('fileInput');
    const progressBar = document.getElementById('progressBar');
    const progressText = document.getElementById('progressText');

    const file = fileInput.files[0];
    const formData = new FormData();
    formData.append('file', file);

    const xhr = new XMLHttpRequest();
    // TODO: Cambiar '/upload' un endpoint posta cuando este
    xhr.open('POST', '/upload', true);

    xhr.upload.onprogress = function(event) {
        if (event.lengthComputable) {
            const percentComplete = (event.loaded / event.total) * 100;
            progressBar.value = percentComplete;
            progressText.textContent = `${Math.round(percentComplete)}% completado`;
        }
    };

    xhr.onload = function() {
        if (xhr.status === 200) {
            progressText.textContent = 'Subida completa!';
        } else {
            progressText.textContent = 'Error en la subida';
        }
    };

    xhr.send(formData);
});