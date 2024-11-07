document.addEventListener('DOMContentLoaded', function () {
    // Obtenemos las referencias de los campos de puntos mínimo y máximo
    const minPointsInput = document.querySelector('#puntos-min'); // Campo de "Puntos Mínimos"
    const maxPointsInput = document.querySelector('#puntos-max'); // Campo de "Puntos Máximos"
    const rubroSelect = document.querySelector('#rubro'); // Campo de "Rubro"
    const offers = document.querySelectorAll('.offer-card'); // Cambiado a '.offer-card' para coincidir con el HTML

    // Función para filtrar las ofertas
    function filterOffers() {
        const minPoints = parseFloat(minPointsInput.value) || 0;
        const maxPoints = parseFloat(maxPointsInput.value) || Infinity;
        const selectedRubro = rubroSelect.value;

        offers.forEach(offer => {
            const points = parseFloat(offer.getAttribute('data-point'));
            const rubro = offer.getAttribute('data-rubro');

            // Verifica si los puntos están dentro del rango y oculta/muestra el recuadro
            if (points >= minPoints && points <= maxPoints && (rubro === selectedRubro || selectedRubro === "Todos")) {
                offer.style.display = 'flex'; // Mostrar si está dentro del rango
            } else {
                offer.style.display = 'none'; // Ocultar si está fuera del rango
            }
        });
    }

    // Event listeners para los inputs
    minPointsInput.addEventListener('input', filterOffers);
    maxPointsInput.addEventListener('input', filterOffers);
    rubroSelect.addEventListener('change', filterOffers);
});


/* document.addEventListener('DOMContentLoaded', function () {
    // Obtenemos las referencias de los campos de puntos mínimo y máximo
    const minPointsInput = document.querySelector('#puntos-min'); // Asume que el campo de "Puntos Mínimos" tiene este id
    const maxPointsInput = document.querySelector('#puntos-max'); // Asume que el campo de "Puntos Máximos" tiene este id
    const rubroSelect = document.querySelector('#rubro'); // Campo de "Rubro"
    const offers = document.querySelectorAll('.offer-card'); // Asume que cada recuadro de oferta tiene la clase 'offer'

    // Función para filtrar las ofertas
    function filterOffers() {
        const minPoints = parseFloat(minPointsInput.value) || 0;
        const maxPoints = parseFloat(maxPointsInput.value) || Infinity;
        //const selectedRubro = rubroSelect.value;
        const selectedRubro = true;
        offers.forEach(offer => {
            const points = parseFloat(offer.getAttribute('data-point'));
            const rubro = offer.getAttribute('data-rubro');

            // Verifica si los puntos y el rubro están dentro de los criterios de filtro
            const pointsInRange = points >= minPoints && points <= maxPoints;
            const rubroMatches = selectedRubro === null || rubro === selectedRubro;
            //const rubroMatches = true;

            // Muestra el recuadro si cumple con ambos criterios
            if (pointsInRange && rubroMatches) {
                offer.style.display = 'block'; // Mostrar si cumple con los filtros
            } else {
                offer.style.display = 'none'; // Ocultar si no cumple con los filtros
            }
        });
    }

    // Event listeners para los inputs
    minPointsInput.addEventListener('input', filterOffers);
    maxPointsInput.addEventListener('input', filterOffers);
});
*/