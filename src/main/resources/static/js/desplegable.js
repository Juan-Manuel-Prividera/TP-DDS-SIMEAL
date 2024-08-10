document.getElementById('btn-expand').addEventListener('click', function() {
    const expandableDiv = document.getElementById('expandible-p' );
    if (expandableDiv.classList.contains('show')) {
        expandableDiv.classList.remove('show');
        expandableDiv.classList.add('hidden');
    } else {
        expandableDiv.classList.remove('hidden');
        expandableDiv.classList.add('show');

    }
});
document.querySelectorAll('.div-expand').forEach(button => {
    button.addEventListener('click', function() {
        const expandableDiv = button.nextElementSibling;

        if (expandableDiv.classList.contains('show')) {
            expandableDiv.classList.remove('show');
            expandableDiv.classList.add('hidden');
        } else {
            expandableDiv.classList.remove('hidden');
            expandableDiv.classList.add('show');

        }
    });
});
