function borrarTarjeta(id) {
    const confirmation = confirm(`¿Seguro queŕes borrar la tarjeta ${id}?`);
    if (confirmation) {
        window.location.href = "https://www.mundogaturro.com/";
    }
}