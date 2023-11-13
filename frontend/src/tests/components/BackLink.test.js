import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';
import BackLink from '../../modules/common/components/BackLink';  // Asegúrate de ajustar la ruta correcta

jest.mock('react-router-dom', () => ({
  useNavigate: jest.fn(),
}));

describe('BackLink Component', () => {
  it('should navigate back when the button is clicked', () => {
    const mockNavigate = jest.fn();
    require('react-router-dom').useNavigate.mockReturnValue(mockNavigate);

    const { getByText } = render(<BackLink />);

    fireEvent.click(getByText('Back'));

    expect(mockNavigate).toHaveBeenCalledWith(-1);
  });
});
