document.getElementById('uploadForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const fileInput = document.getElementById('fileInput');
    const progressBar = document.getElementById('progressBar');
    const progressText = document.getElementById('progressText');

    const file = fileInput.files[0];
    const formData = new FormData();
    formData.append('file', file);

    const xhr = new XMLHttpRequest();
    xhr.open('POST', '/migracion/upload', true);

    let simulatedProgress = 0;
    const interval = setInterval(() => {
        if (simulatedProgress < 50) {
            simulatedProgress += 1; // Incrementa el progreso simulado de 1 en 1 hasta 50
            progressBar.value = simulatedProgress;
            progressText.textContent = `Cargando... ${simulatedProgress}%`;
        } else {
            clearInterval(interval); // Detener el incremento simulado al llegar al 50%
        }
    }, 50); // Ajusta este valor para cambiar la velocidad del incremento

// Actualizar el progreso de la carga
    xhr.upload.onprogress = function(event) {
        if (event.lengthComputable) {
            const percentComplete = 50 + (event.loaded / event.total) * 50; // Inicia desde 50%
            progressBar.value = percentComplete;
            progressText.textContent = `${Math.round(percentComplete)}% completado`;
        }
    };

    // Después de recibir 200 OK, esperar a que el progreso lento termine y luego incrementar rápidamente
    xhr.onload = function() {
        if (xhr.status === 200) {
            // Espera a que el progreso lento termine antes de iniciar el incremento rápido
            setTimeout(() => {
                let fastProgress = simulatedProgress;
                const fastInterval = setInterval(() => {
                    if (fastProgress < 100) {
                        fastProgress += 5; // Incremento rápido hasta el 100%
                        progressBar.value = fastProgress;
                        progressText.textContent = `${Math.round(fastProgress)}% completado`;
                    } else {
                        clearInterval(fastInterval); // Detener el incremento rápido
                        progressText.textContent = 'El archivo csv se cargo correctamente, en breve se completara el proceso de migracion. ' +
                                                    '\n Se le notificara cuando el proceso haya terminado.';
                    }
                }, 30); // Intervalo rápido para alcanzar el 100%
            }, (50 - simulatedProgress) * 50); // Espera a que termine el incremento lento
        } else {
            progressText.textContent = 'Ocurrio un error al cargar el archivo csv, por favor revisar que el formato del mismo sea el indicado y volver a intentar.';
        }
    };

    xhr.onerror = function() {
        progressText.textContent = 'Ocurrio un error al enviar el formulario, por favor revise su conexión y vuelva a intentarlo.';
    };

// Enviar el archivo
    xhr.send(formData);
});