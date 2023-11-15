import React from 'react';
import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import PostLink from '../../modules/common/components/PostLink';
import '@testing-library/jest-dom/extend-expect';

describe('PostLink Component', () => {
  it('should render without crashing', () => {
    const { getByText } = render(
      <MemoryRouter>
        <PostLink id={1} title="Test Post" />
      </MemoryRouter>
    );
    expect(getByText('Test Post')).toBeInTheDocument();
  });

  it('should render a Link with the correct "to" prop', () => {
    const { getByText } = render(
      <MemoryRouter>
        <PostLink id={123} title="Test Post" />
      </MemoryRouter>
    );
    
    const link = getByText('Test Post').closest('a');
    expect(link).toHaveAttribute('href', '/post/post-details/123');
  });

  it('should match the snapshot', () => {
    const { asFragment } = render(
      <MemoryRouter>
        <PostLink id={1} title="Test Post" />
      </MemoryRouter>
    );
    
    expect(asFragment()).toMatchSnapshot();
  });
});
