'use strict';

const { describe, it, before } = require('node:test');
const assert = require('node:assert/strict');
const { encodeTraceRequest, decodeTraceRequest, processMessage } = require('./consumer');

describe('opentelemetry-consumer-1 protobuf handling', () => {
  let encodedBuffer;

  const samplePayload = {
    resourceSpans: [
      {
        resource: {
          attributes: [{ key: 'service.name', value: { stringValue: 'test-service' } }],
        },
        scopeSpans: [
          {
            scope: { name: 'test-scope', version: '1.0.0' },
            spans: [
              {
                name: 'test-span',
                kind: 2, // SPAN_KIND_SERVER
                status: { code: 1 }, // STATUS_CODE_OK
              },
            ],
          },
        ],
      },
    ],
  };

  before(async () => {
    encodedBuffer = await encodeTraceRequest(samplePayload);
  });

  it('should encode a trace request to a Buffer', async () => {
    assert.ok(Buffer.isBuffer(encodedBuffer), 'result should be a Buffer');
    assert.ok(encodedBuffer.length > 0, 'buffer should not be empty');
  });

  it('should decode an encoded trace request back to an object', async () => {
    const decoded = await decodeTraceRequest(encodedBuffer);
    assert.ok(decoded, 'decoded result should be truthy');
    assert.ok(Array.isArray(decoded.resourceSpans), 'resourceSpans should be an array');
    assert.equal(decoded.resourceSpans[0].scopeSpans[0].spans[0].name, 'test-span');
  });

  it('should round-trip encode then decode without data loss', async () => {
    // Re-encode a payload using numeric enum values (as protobufjs verify expects)
    // and confirm the span name survives the round-trip.
    const reEncoded = await encodeTraceRequest(samplePayload);
    const reDecoded = await decodeTraceRequest(reEncoded);
    assert.equal(
      reDecoded.resourceSpans[0].scopeSpans[0].spans[0].name,
      'test-span'
    );
  });

  it('processMessage should invoke the handler with the decoded object', async () => {
    let handlerResult = null;
    await processMessage(encodedBuffer, async (decoded) => {
      handlerResult = decoded;
    });
    assert.ok(handlerResult !== null);
    assert.equal(handlerResult.resourceSpans[0].scopeSpans[0].spans[0].name, 'test-span');
  });

  it('processMessage should throw TypeError for invalid buffer argument', async () => {
    await assert.rejects(
      () => processMessage('not-a-buffer', async () => {}),
      TypeError
    );
  });

  it('processMessage should throw TypeError for non-function handler', async () => {
    await assert.rejects(
      () => processMessage(encodedBuffer, 'not-a-function'),
      TypeError
    );
  });
});
