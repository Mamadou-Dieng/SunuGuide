import React from 'react';
import { Home, Map, MessageSquare, User } from 'lucide-react';

export default function Navbar({ currentPage, onNavigate }) {
  const navItems = [
    { id: 'home', icon: Home, label: 'Accueil' },
    { id: 'map', icon: Map, label: 'Carte' },
    { id: 'chat', icon: MessageSquare, label: 'Chat' },
    { id: 'profile', icon: User, label: 'Profil' }
  ];

  return (
    <div className="bg-white border-top p-3">
      <div className="d-flex justify-content-around">
        {navItems.map((item) => (
          <button
            key={item.id}
            onClick={() => onNavigate(item.id)}
            className={`btn btn-link text-decoration-none d-flex flex-column align-items-center gap-1 ${
              currentPage === item.id ? 'text-primary' : 'text-secondary'
            }`}
          >
            <item.icon size={24} />
            <span style={{ fontSize: '0.75rem' }}>{item.label}</span>
          </button>
        ))}
      </div>
    </div>
  );
}