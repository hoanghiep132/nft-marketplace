import React, {useEffect, useState} from "react";
import {Select} from "antd";
import styled from "styled-components";
import {searchCollection} from "../../modules/Collection/services/apis";
import {useHistory} from "react-router";

const SearchCollections = () => {
    const {Option} = Select;
    const [nftCollections, setNftCollections] = useState<any>([]);

    const [inputValue, setInputValue] = useState<string>();

    const history = useHistory();

    useEffect(() => {
        getCollectionOptionList('');
    }, []);

    function onSearch(value: any) {
        setInputValue(value);
        getCollectionOptionList(value);
    }

    const getCollectionOptionList = (value: string) => {
        const searchParam = {
            text: value,
            page: 1,
            size: 5
        }
        searchCollection(searchParam).then(rs => {
            if (rs.code != 0) {
                setNftCollections([]);
            }
            setNftCollections(rs.rows);
        }).catch(() => setNftCollections([]));
    }

    const onChange = (value: any) => {
        setInputValue('');
        history.push('/collection/' + value);
    }

    const renderOptionList = (): any => {
        if (nftCollections.length == 0) {
            return null;
        }
        return nftCollections.map((collection: any) => {
                return (
                    <Option value={collection.uuid} key={collection.uuid}>
                        <div style={{display: 'flex', width: '400px'}}>
                            <div style={{width: '20%'}}>
                                <img alt="test" src={collection.image}/>
                            </div>
                            <div style={{width: '70%', marginLeft: '20px'}}>
                                <p>{collection.name} ({collection.symbol})</p>
                            </div>
                        </div>
                    </Option>
                )
            }
        );
    }

    return (
        <SelectStyled
            showSearch
            placeholder="Tìm kiếm bộ sưu tập"
            optionFilterProp="children"
            value={inputValue}
            filterOption={false}
            onSearch={onSearch}
            onChange={onChange}
        >
            {renderOptionList()}
        </SelectStyled>

    )
}
export default SearchCollections;

const SelectStyled = styled(Select)`
    border-radius: 8px;
    width: 1000px;
    
    .ant-select-selector {
        border-radius: 8px !important;

    }
`
