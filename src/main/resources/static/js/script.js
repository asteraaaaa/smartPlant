document.addEventListener('DOMContentLoaded', () => {
    const sidebar = document.getElementById('sidebar');
    const toggleButtons = document.querySelectorAll('#sidebarToggle');

    function setCookie(name, value, days) {
        const expires = new Date(Date.now() + days * 864e5).toUTCString();
        document.cookie = name + '=' + encodeURIComponent(value) + '; expires=' + expires + '; path=/';
    }

    function getCookie(name) {
        const match = document.cookie.split('; ').find(c => c.startsWith(name + '='));
        return match ? decodeURIComponent(match.split('=')[1]) : null;
    }

    // restore the saved sidebar state
    if (sidebar && getCookie('sidebar-collapsed') === 'true') {
        sidebar.classList.add('collapsed');
    }

    toggleButtons.forEach(btn => {
        btn.addEventListener('click', () => {
            if (!sidebar) return;
            sidebar.classList.toggle('collapsed');
            setCookie('sidebar-collapsed', sidebar.classList.contains('collapsed'), 7);
        });
    });

    // header clock
    const clock = document.getElementById('clock');
    if (clock) {
        const tick = () => { clock.textContent = new Date().toLocaleTimeString(); };
        tick();
        setInterval(tick, 1000);
    }

    // pages that show live sensor data opt in with data-autorefresh="<seconds>"
    const auto = document.querySelector('[data-autorefresh]');
    if (auto) {
        const seconds = parseInt(auto.dataset.autorefresh, 10);
        if (seconds > 0) {
            setInterval(() => {
                // don't reload while the user is typing in a form or a dialog could be open
                const el = document.activeElement;
                const busy = el && ['INPUT', 'SELECT', 'TEXTAREA'].includes(el.tagName);
                if (!document.hidden && !busy) location.reload();
            }, seconds * 1000);
        }
    }
});
