document.querySelectorAll('.tarjeta-persona-vulnerable').forEach(function(tarjeta) {
    tarjeta.addEventListener('click', function() {
        var expandibleDiv = tarjeta.nextElementSibling; // Selecciona el div expandible que sigue a la tarjeta
        if (expandibleDiv.classList.contains('hidden')) {
            expandibleDiv.classList.remove('hidden');
            expandibleDiv.classList.add('show');
        } else {
            expandibleDiv.classList.remove('show');
            setTimeout(function() {
                expandibleDiv.classList.add('hidden');
            }, 0);
        }
    });
});
