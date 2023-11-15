import React from 'react';
import { render, screen } from '@testing-library/react';
import ExternalLinkButton from '../../modules/common/components/ExternalLinkButton';
import '@testing-library/jest-dom/extend-expect';

describe('ExternalLinkButton', () => {
  test('renders with correct URL', () => {
    const url = 'https://example.com';
    render(<ExternalLinkButton url={url} />);

    const link = screen.getByRole('link');
    expect(link).toHaveAttribute('href', url);
  });

  test('renders with correct class', () => {
    const url = 'https://example.com';
    render(<ExternalLinkButton url={url} />);

    const link = screen.getByRole('link');
    expect(link).toHaveClass('btn', 'btn-primary');
  });

  test('renders the SVG icon', () => {
    const url = 'https://example.com';
    render(<ExternalLinkButton url={url} />);

    const link = screen.getByRole('link');
    const svgIcon = link.querySelector('svg');
    expect(svgIcon).toBeInTheDocument();
  });
});
