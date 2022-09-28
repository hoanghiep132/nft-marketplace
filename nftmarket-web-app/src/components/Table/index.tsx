import {Skeleton, Table} from 'antd';
import React, {useEffect, useState} from 'react';
import SkeletonTable from '../SkeletonTable';
import useWindowResize from 'src/hooks/useWindowResize';
import env from 'src/configs/env';
import Pagination from '../Pagination';
import {IPagination} from 'src/models/common';

interface ITableProps {
  loading: boolean;
  key?: string;
  columns: any;
  dataSource: any;
  pagingTable?: false | undefined;
  size?: 'default' | 'middle' | 'small';
  totalRow?: number;
  pagination?: IPagination;
  onChangePage?: (page: number) => void;
}

interface IScrollTable {
  x: boolean | string;
}
const TableEdiable = (props: ITableProps) => {
  const {
    loading,
    columns,
    key = 'name',
    dataSource,
    pagingTable = false,
    size = 'small',
    totalRow = 0,
    pagination = { page: 1, size: 10 },
    onChangePage = () => {},
  } = props;
  const [screenWidth, _] = useWindowResize();
  const [scroll, setScroll] = useState<IScrollTable>({ x: false });
  useEffect(() => {
    const scroll = screenWidth < env.desktopWidth ? { x: 'fit-content' } : { x: false };
    setScroll(scroll);
  }, [screenWidth]);

  return (
    <div>
      <SkeletonTable loading={loading} columns={columns}>
        <Table
          className="custom-table"
          columns={columns}
          dataSource={dataSource}
          scroll={scroll}
          pagination={pagingTable}
          rowKey={key}
          size={size}
        />
      </SkeletonTable>
      {pagingTable === false && (
        <Skeleton loading={loading} paragraph={{ rows: 1 }}>
          <Pagination onChangePage={onChangePage} totalRow={totalRow} pagination={pagination} />
        </Skeleton>
      )}
    </div>
  );
};

export default TableEdiable;
