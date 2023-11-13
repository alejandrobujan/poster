import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import Pager from '../../modules/common/components/Pager';
import '@testing-library/jest-dom/extend-expect';

describe('Pager Component', () => {
  test('disables back button when not enabled', () => {
    const backMock = jest.fn();
    const nextMock = jest.fn();

    const { getByText } = render(
      <Pager
        back={{ enabled: false, onClick: backMock }}
        next={{ enabled: true, onClick: nextMock }}
      />
    );

    const backButton = getByText('Back');
    const nextButton = getByText('Next');

    expect(backButton).toBeInTheDocument();
    expect(nextButton).toBeInTheDocument();

    if (backButton.hasAttribute('disabled')) {
      expect(backButton.getAttribute('disabled')).toBeTruthy();
    }

    if (backButton.hasAttribute('aria-disabled')) {
      expect(backButton.getAttribute('aria-disabled')).toBe('true');
    }

    expect(nextButton).not.toHaveAttribute('disabled');

    if (nextButton.hasAttribute('aria-disabled')) {
      expect(nextButton.getAttribute('aria-disabled')).toBe('false');
    }

    fireEvent.click(backButton);

    fireEvent.click(nextButton);
    expect(nextMock).toHaveBeenCalledTimes(1);
  });

  test('disables next button when not enabled', () => {
    const backMock = jest.fn();
    const nextMock = jest.fn();

    const { getByText } = render(
      <Pager
        back={{ enabled: true, onClick: backMock }}
        next={{ enabled: false, onClick: nextMock }}
      />
    );

    const backButton = getByText('Back');
    const nextButton = getByText('Next');

    expect(backButton).not.toHaveAttribute('disabled');

    if (backButton.hasAttribute('aria-disabled')) {
      expect(backButton.getAttribute('aria-disabled')).toBe('false');
    }

    expect(nextButton).toBeInTheDocument();

    if (nextButton.hasAttribute('disabled')) {
      expect(nextButton.getAttribute('disabled')).toBeTruthy();
    }

    if (nextButton.hasAttribute('aria-disabled')) {
      expect(nextButton.getAttribute('aria-disabled')).toBe('true');
    }

    fireEvent.click(backButton);
    expect(backMock).toHaveBeenCalledTimes(1);
  });
});
