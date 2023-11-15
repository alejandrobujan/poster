import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import Errors from '../../modules/common/components/Errors';
import '@testing-library/jest-dom/extend-expect';

test('renders without errors', () => {
  render(<Errors />);
  expect(screen.queryByText(/alert-danger/)).toBeNull();
});

test('renders global error', () => {
  const errors = { globalError: 'Global error message' };
  render(<Errors errors={errors} />);
  expect(screen.getByText('Global error message')).toBeInTheDocument();
});

test('renders field errors', () => {
  const errors = {
    fieldErrors: [
      { fieldName: 'field1', message: 'Error 1' },
      { fieldName: 'field2', message: 'Error 2' },
    ],
  };
  render(<Errors errors={errors} />);
  expect(screen.getByText('field1: Error 1')).toBeInTheDocument();
  expect(screen.getByText('field2: Error 2')).toBeInTheDocument();
});

test('calls onClose when close button is clicked', () => {
  const errors = { globalError: 'Global error message' };
  const onCloseMock = jest.fn();
  render(<Errors errors={errors} onClose={onCloseMock} />);

  fireEvent.click(screen.getByLabelText(/Close/));

  expect(onCloseMock).toHaveBeenCalledTimes(1);
});