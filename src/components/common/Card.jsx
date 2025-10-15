import React from 'react';

export default function Card({ children, className = '', onClick }) {
  return (
    <div 
      className={`bg-white border-2 border-gray-200 rounded-2xl shadow-sm ${className}`}
      onClick={onClick}
    >
      {children}
    </div>
  );
}